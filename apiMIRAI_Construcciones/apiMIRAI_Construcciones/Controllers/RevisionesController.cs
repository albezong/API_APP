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
    public class RevisionesController : ApiController
    {
        private AlmacenTAEPIEntities db = new AlmacenTAEPIEntities();

        // GET: api/Revisiones
        public IQueryable<Revisiones> GetRevisiones()
        {
            return db.Revisiones;
        }

        // GET: api/Revisiones/5
        [ResponseType(typeof(Revisiones))]
        public IHttpActionResult GetRevisiones(int id)
        {
            Revisiones revisiones = db.Revisiones.Find(id);
            if (revisiones == null)
            {
                return NotFound();
            }

            return Ok(revisiones);
        }

        // PUT: api/Revisiones/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutRevisiones(int id, Revisiones revisiones)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != revisiones.idRevisiones)
            {
                return BadRequest();
            }

            db.Entry(revisiones).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!RevisionesExists(id))
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

        // POST: api/Revisiones
        [ResponseType(typeof(Revisiones))]
        public IHttpActionResult PostRevisiones(Revisiones revisiones)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Revisiones.Add(revisiones);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = revisiones.idRevisiones }, revisiones);
        }

        // DELETE: api/Revisiones/5
        [ResponseType(typeof(Revisiones))]
        public IHttpActionResult DeleteRevisiones(int id)
        {
            Revisiones revisiones = db.Revisiones.Find(id);
            if (revisiones == null)
            {
                return NotFound();
            }

            db.Revisiones.Remove(revisiones);
            db.SaveChanges();

            return Ok(revisiones);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool RevisionesExists(int id)
        {
            return db.Revisiones.Count(e => e.idRevisiones == id) > 0;
        }
    }
}