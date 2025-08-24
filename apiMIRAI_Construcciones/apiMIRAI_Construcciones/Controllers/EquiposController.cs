using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web;
using System.Web.Http;
using System.Web.Http.Description;
using APIMIRAI_Construcciones.Data;
using APIMIRAI_Construcciones.Helper;
using APIMIRAI_Construcciones.Models;

namespace APIMIRAI_Construcciones.Controllers
{
    public class EquiposController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/Equipos
        public IHttpActionResult GetEquipos()
        {
            var equipos = db.Equipos
                .Select(e => new EquiposDto
                {
                    idEquipos = e.idEquipos,
                    codigoArticulo = e.codigoArticulo,
                    nombreArticulo = e.nombreArticulo,
                    nombreComercial = e.nombreComercial,
                    numIdentificador = e.numIdentificador,
                    descripcion = e.descripcion,
                    marca = e.marca,
                    modelo = e.modelo,
                    fechadeRegistro = e.fechadeRegistro,
                    idfUbicaciones = e.idfUbicaciones,
                    idfUnidades = e.idfUnidades,
                    idfEstatus = e.idfEstatus,
                    idfTiposMaquinarias = e.idfTiposMaquinarias,
                })
                .ToList();


            return Ok(equipos);
        }

        // GET: api/Equipos/5
        [ResponseType(typeof(Equipos))]
        public IHttpActionResult GetEquipos(int id)
        {
            var empresa = db.Equipos
        .Where(e => e.idEquipos == id)
        .Select(e => new EquiposDto
        {
            idEquipos = e.idEquipos,
            codigoArticulo = e.codigoArticulo,
            nombreArticulo = e.nombreArticulo,
            nombreComercial = e.nombreComercial,
            numIdentificador = e.numIdentificador,
            descripcion = e.descripcion,
            marca = e.marca,
            modelo = e.modelo,
            fechadeRegistro = e.fechadeRegistro,
            idfUbicaciones = e.idfUbicaciones,
            idfUnidades = e.idfUnidades,
            idfEstatus = e.idfEstatus,
            idfTiposMaquinarias = e.idfTiposMaquinarias,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
        }

        // PUT: api/Equipos/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutEquipos(int id, Equipos equipos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != equipos.idEquipos)
            {
                return BadRequest();
            }

            db.Entry(equipos).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!EquiposExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Equipos
        [ResponseType(typeof(Equipos))]
        public IHttpActionResult PostEquipos_Se_crea_QR_solo(Equipos equipos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Equipos.Add(equipos);
            db.SaveChanges(); 

            var datos =
                $"Codigo del Articulo:{equipos.codigoArticulo}, " +
                $"Nombre del Articulo:{equipos.nombreArticulo}, " +
                $"Nombre Comercial:{equipos.nombreComercial}, " +
                $"Numero Identificador:{equipos.numIdentificador}, " +
                $"Descripcion:{equipos.descripcion}, " +
                $"Marca:{equipos.marca}, " +
                $"Modelo:{equipos.modelo}, " +
                $"Fecha de Registro:{equipos.fechadeRegistro}, " +
                $"Ubicacion:{equipos.Ubicaciones.nombre}, " +
                $"Unidad:{equipos.Unidades.nombre}, " +
                $"Estatus:{equipos.Estatus.nombre}, " +
                $"Tipo de Maquinaria:{equipos.TiposMaquinarias.nombre}, " +
                $"Se encuentra en:{equipos.Lugares.Where(r=>r.idfEquipos == equipos.idEquipos).Select(y=>y.nombreLugar).Distinct()}";
            var qrImagen = QrCodeGeneratorHelper.GenerateQRCode(datos); 

            var qrBase64 = Convert.ToBase64String(qrImagen);
            
            var qrEquipo = new QrEquipos
            {
                idEquipos = equipos.idEquipos,
                claveQR = qrBase64
            };

            db.QrEquipos.Add(qrEquipo);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = equipos.idEquipos }, new
            {
                Equipo = equipos,
                Qr = qrEquipo
            });
        }

        // DELETE: api/Equipos/5
        [ResponseType(typeof(Equipos))]
        public IHttpActionResult DeleteEquipos(int id)
        {
            Equipos equipos = db.Equipos.Find(id);
            if (equipos == null)
            {
                return NotFound();
            }

            db.Equipos.Remove(equipos);
            db.SaveChanges();

            return Ok(equipos);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool EquiposExists(int id)
        {
            return db.Equipos.Count(e => e.idEquipos == id) > 0;
        }

    }
}