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
    public class EstatusController : ApiController
    {
        private AlmacenTAEPIEntities db = new AlmacenTAEPIEntities();

        // GET: api/Estatus
        public IQueryable<Estatus> GetEstatus()
        {
            return db.Estatus;
        }

        // GET: api/Estatus/5
        [ResponseType(typeof(Estatus))]
        public IHttpActionResult GetEstatus(int id)
        {
            Estatus estatus = db.Estatus.Find(id);
            if (estatus == null)
            {
                return NotFound();
            }

            return Ok(estatus);
        }

        // PUT: api/Estatus/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutEstatus(int id, Estatus estatus)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != estatus.idEstatus)
            {
                return BadRequest();
            }

            db.Entry(estatus).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!EstatusExists(id))
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

        // POST: api/Estatus
        [ResponseType(typeof(Estatus))]
        public IHttpActionResult PostEstatus(Estatus estatus)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Estatus.Add(estatus);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = estatus.idEstatus }, estatus);
        }

        // DELETE: api/Estatus/5
        [ResponseType(typeof(Estatus))]
        public IHttpActionResult DeleteEstatus(int id)
        {
            Estatus estatus = db.Estatus.Find(id);
            if (estatus == null)
            {
                return NotFound();
            }

            db.Estatus.Remove(estatus);
            db.SaveChanges();

            return Ok(estatus);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool EstatusExists(int id)
        {
            return db.Estatus.Count(e => e.idEstatus == id) > 0;
        }
    }
}