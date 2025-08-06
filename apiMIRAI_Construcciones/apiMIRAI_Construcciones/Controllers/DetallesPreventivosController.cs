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
    public class DetallesPreventivosController : ApiController
    {
        private AlmacenTAEPIEntities db = new AlmacenTAEPIEntities();

        // GET: api/DetallesPreventivos
        public IQueryable<DetallesPreventivos> GetDetallesPreventivos()
        {
            return db.DetallesPreventivos;
        }

        // GET: api/DetallesPreventivos/5
        [ResponseType(typeof(DetallesPreventivos))]
        public IHttpActionResult GetDetallesPreventivos(int id)
        {
            DetallesPreventivos detallesPreventivos = db.DetallesPreventivos.Find(id);
            if (detallesPreventivos == null)
            {
                return NotFound();
            }

            return Ok(detallesPreventivos);
        }

        // PUT: api/DetallesPreventivos/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutDetallesPreventivos(int id, DetallesPreventivos detallesPreventivos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != detallesPreventivos.idDetallesPreventivos)
            {
                return BadRequest();
            }

            db.Entry(detallesPreventivos).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!DetallesPreventivosExists(id))
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

        // POST: api/DetallesPreventivos
        [ResponseType(typeof(DetallesPreventivos))]
        public IHttpActionResult PostDetallesPreventivos(DetallesPreventivos detallesPreventivos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.DetallesPreventivos.Add(detallesPreventivos);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = detallesPreventivos.idDetallesPreventivos }, detallesPreventivos);
        }

        // DELETE: api/DetallesPreventivos/5
        [ResponseType(typeof(DetallesPreventivos))]
        public IHttpActionResult DeleteDetallesPreventivos(int id)
        {
            DetallesPreventivos detallesPreventivos = db.DetallesPreventivos.Find(id);
            if (detallesPreventivos == null)
            {
                return NotFound();
            }

            db.DetallesPreventivos.Remove(detallesPreventivos);
            db.SaveChanges();

            return Ok(detallesPreventivos);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool DetallesPreventivosExists(int id)
        {
            return db.DetallesPreventivos.Count(e => e.idDetallesPreventivos == id) > 0;
        }
    }
}