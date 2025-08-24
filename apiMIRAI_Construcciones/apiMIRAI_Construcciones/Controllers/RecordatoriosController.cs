using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using APIMIRAI_Construcciones.Data;
using APIMIRAI_Construcciones.Models;

namespace APIMIRAI_Construcciones.Controllers
{
    public class RecordatoriosController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/Recordatorios
        public IHttpActionResult GetRecordatorios()
        {
            var empresas = db.Recordatorios
                .Select(e => new RecordatoriosDto
                {
                    idRecordatorios = e.idRecordatorios,
                    idfEquipos = e.idfEquipos,
                    idfTareas = e.idfTareas,
                    idfUsuarios = e.idfUsuarios,
                    idfPrioridades = e.idfPrioridades,
                    idfTiposMantenimientos = e.idfTiposMantenimientos,
                    recordarS_N = e.recordarS_N,
                    fechaRecordatorio = e.fechaRecordatorio,
                    numeroReporte = e.numeroReporte,
                    descripcion = e.descripcion,
                })
                .ToList();

            return Ok(empresas);
        }

        // GET: api/Recordatorios/5
        [ResponseType(typeof(Recordatorios))]
        public IHttpActionResult GetRecordatorios(int id)
        {
            var empresa = db.Recordatorios
        .Where(e => e.idRecordatorios == id)
        .Select(e => new RecordatoriosDto
        {
            idRecordatorios = e.idRecordatorios,
            idfEquipos = e.idfEquipos,
            idfTareas = e.idfTareas,
            idfUsuarios = e.idfUsuarios,
            idfPrioridades = e.idfPrioridades,
            idfTiposMantenimientos = e.idfTiposMantenimientos,
            recordarS_N = e.recordarS_N,
            fechaRecordatorio = e.fechaRecordatorio,
            numeroReporte = e.numeroReporte,
            descripcion = e.descripcion,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
        }

        // PUT: api/Recordatorios/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutRecordatorios(int id, Recordatorios recordatorios)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != recordatorios.idRecordatorios)
            {
                return BadRequest();
            }

            db.Entry(recordatorios).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!RecordatoriosExists(id))
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

        // POST: api/Recordatorios
        [ResponseType(typeof(Recordatorios))]
        public IHttpActionResult PostRecordatorios(Recordatorios recordatorios)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Recordatorios.Add(recordatorios);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = recordatorios.idRecordatorios }, recordatorios);
        }

        // DELETE: api/Recordatorios/5
        [ResponseType(typeof(Recordatorios))]
        public IHttpActionResult DeleteRecordatorios(int id)
        {
            Recordatorios recordatorios = db.Recordatorios.Find(id);
            if (recordatorios == null)
            {
                return NotFound();
            }

            db.Recordatorios.Remove(recordatorios);
            db.SaveChanges();

            return Ok(recordatorios);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool RecordatoriosExists(int id)
        {
            return db.Recordatorios.Count(e => e.idRecordatorios == id) > 0;
        }
    }
}