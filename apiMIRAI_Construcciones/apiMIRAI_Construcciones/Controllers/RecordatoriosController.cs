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
using apiMIRAI_Construcciones.Data;

namespace apiMIRAI_Construcciones.Controllers
{
    public class RecordatoriosController : ApiController
    {
        private AlmacenTAEPIEntities db = new AlmacenTAEPIEntities();

        // GET: api/Recordatorios
        public IQueryable<Recordatorios> GetRecordatorios()
        {
            return db.Recordatorios;
        }

        // GET: api/Recordatorios/5
        [ResponseType(typeof(Recordatorios))]
        public IHttpActionResult GetRecordatorios(int id)
        {
            Recordatorios recordatorios = db.Recordatorios.Find(id);
            if (recordatorios == null)
            {
                return NotFound();
            }

            return Ok(recordatorios);
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