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
using System.Threading.Tasks;
using System.Web;
using System.Web.Http;
using System.Web.Http.Description;
using APIMIRAI_Construcciones.Data;
using APIMIRAI_Construcciones.Helper;
using APIMIRAI_Construcciones.Models;

namespace APIMIRAI_Construcciones.Controllers
{
    [RoutePrefix("api/Equipos")]
    public class EquiposController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/Equipos
        [HttpGet]
        [Route("")]
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
                    idfUbicaciones = e.idfUbicaciones,//
                    idfUnidades = e.idfUnidades,
                    idfEstatus = e.idfEstatus,
                    idfTiposMaquinarias = e.idfTiposMaquinarias,
                })
                .ToList();


            return Ok(equipos);
        }

        // GET: api/Equipos/5
        //[ResponseType(typeof(Equipos))]
        [HttpGet]
        [Route("{id:int}")]
        public IHttpActionResult GetEquipos(int id)
        {
            var equipos = db.Equipos
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

            if (equipos == null)
            {
                return NotFound();
            }

            return Ok(equipos);
        }

        // PUT: api/Equipos/5
        //[ResponseType(typeof(void))]
        [HttpPut]
        [Route("{id:int}")]
        public IHttpActionResult UpdateEquipo(int id, EquiposDto equipo)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var equipoExistente = db.Equipos.Find(id);
            if (equipoExistente == null)
                return NotFound();

            if (id != equipo.idEquipos)
                return BadRequest();

            equipoExistente.codigoArticulo = equipo.codigoArticulo;
            equipoExistente.nombreArticulo = equipo.nombreArticulo;
            equipoExistente.nombreComercial = equipo.nombreComercial;
            equipoExistente.numIdentificador = equipo.numIdentificador;
            equipoExistente.descripcion = equipo.descripcion;
            equipoExistente.marca = equipo.marca;
            equipoExistente.modelo = equipo.modelo;
            equipoExistente.fechadeRegistro = equipo.fechadeRegistro;
            equipoExistente.idfUbicaciones = equipo.idfUbicaciones;
            equipoExistente.idfUnidades = equipo.idfUnidades;
            equipoExistente.idfEstatus = equipo.idfEstatus;
            equipoExistente.idfTiposMaquinarias = equipo.idfTiposMaquinarias;

            db.SaveChanges();

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
        //[ResponseType(typeof(Equipos))]
        /*[HttpPost]
        [Route("")]
        public IHttpActionResult PostEquipos_Se_crea_QR_solo(Equipos equipo)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Equipos.Add(equipo);
            db.SaveChanges();

            var equipoCompleto = db.Equipos
                .Include("Ubicaciones")
                .Include("Unidades")
                .Include("Estatus")
                .Include("TiposMaquinarias")
                .FirstOrDefault(e => e.idEquipos == equipo.idEquipos);

            var datos =
                $"Codigo del Articulo:{equipoCompleto.codigoArticulo}, " +
                $"Nombre del Articulo:{equipoCompleto.nombreArticulo}, " +
                $"Nombre Comercial:{equipoCompleto.nombreComercial}, " +
                $"Numero Identificador:{equipoCompleto.numIdentificador}, " +
                $"Descripcion:{equipoCompleto.descripcion}, " +
                $"Marca:{equipoCompleto.marca}, " +
                $"Modelo:{equipoCompleto.modelo}, " +
                $"Fecha de Registro:{equipoCompleto.fechadeRegistro}, " +
                $"Ubicacion:{equipoCompleto.Ubicaciones?.nombre ?? "Sin Ubicación"}, " +
                $"Unidad:{equipoCompleto.Unidades?.nombre ?? "Sin Unidad"}, " +
                $"Estatus:{equipoCompleto.Estatus?.nombre ?? "Sin Estatus"}, " +
                $"Tipo de Maquinaria:{equipoCompleto.TiposMaquinarias?.nombre ?? "Sin Tipo"}, ";
                //$"Se encuentra en:{equipoCompleto.Lugares.FirstOrDefault()?.nombreLugar ?? "Sin Lugar"}";
            var qrImagen = QrCodeGeneratorHelper.GenerateQRCode(datos);

            var qrBase64 = Convert.ToBase64String(qrImagen);

            var qrEquipo = new QrEquipos
            {
                idEquipos = equipo.idEquipos,
                claveQR = qrBase64
            };

            db.QrEquipos.Add(qrEquipo);
            db.SaveChanges();

            return Ok(equipo);
        }*/
        [HttpPost]
        [Route("")]
        public async Task<IHttpActionResult> PostEquipos_Se_crea_QR_solo(Equipos equipo)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Equipos.Add(equipo);
            await db.SaveChangesAsync();

            var equipoCompleto = await db.Equipos
                .Include("Ubicaciones")
                .Include("Unidades")
                .Include("Estatus")
                .Include("TiposMaquinarias")
                .FirstOrDefaultAsync(e => e.idEquipos == equipo.idEquipos);

            var datos =
                $"Codigo del Articulo:{equipoCompleto.codigoArticulo}, " +
                $"Nombre del Articulo:{equipoCompleto.nombreArticulo}, " +
                $"Nombre Comercial:{equipoCompleto.nombreComercial}, " +
                $"Numero Identificador:{equipoCompleto.numIdentificador}, " +
                $"Descripcion:{equipoCompleto.descripcion}, " +
                $"Marca:{equipoCompleto.marca}, " +
                $"Modelo:{equipoCompleto.modelo}, " +
                $"Fecha de Registro:{equipoCompleto.fechadeRegistro}, " +
                $"Ubicacion:{equipoCompleto.Ubicaciones?.nombre ?? "Sin Ubicación"}, " +
                $"Unidad:{equipoCompleto.Unidades?.nombre ?? "Sin Unidad"}, " +
                $"Estatus:{equipoCompleto.Estatus?.nombre ?? "Sin Estatus"}, " +
                $"Tipo de Maquinaria:{equipoCompleto.TiposMaquinarias?.nombre ?? "Sin Tipo"}";

            var qrImagen = QrCodeGeneratorHelper.GenerateQRCode(datos);
            var qrBase64 = Convert.ToBase64String(qrImagen);

            var qrEquipo = new QrEquipos
            {
                idEquipos = equipo.idEquipos,
                claveQR = qrBase64
            };

            db.QrEquipos.Add(qrEquipo);
            await db.SaveChangesAsync();

            // Devolver el objeto completo junto con el QR
            /*return Ok(new
            {
                equipo = equipoCompleto,
                qr = qrBase64
            });*/
            return StatusCode(HttpStatusCode.NoContent);
        }


        // DELETE: api/Equipos/5
        [HttpDelete]
        [Route("{id:int}")]
        public IHttpActionResult DeleteEquipo(int id)
        {
            var equipo = db.Equipos.Find(id);
            if (equipo == null)
                return NotFound();

            // Buscar el QR relacionado
            var qr = db.QrEquipos.FirstOrDefault(q => q.idEquipos == id);
            if (qr != null)
            {
                db.QrEquipos.Remove(qr);
            }

            // Eliminar el equipo
            db.Equipos.Remove(equipo);
            db.SaveChanges();

            return Ok(new { message = "Equipo y QR eliminados correctamente", equipo });
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