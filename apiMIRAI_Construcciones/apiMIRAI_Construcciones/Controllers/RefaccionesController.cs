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
    public class RefaccionesController : ApiController
    {
        private AlmacenTAEPIEntities db = new AlmacenTAEPIEntities();

        // GET: api/Refacciones
        public IQueryable<Refacciones> GetRefacciones()
        {
            return db.Refacciones;
        }

        // GET: api/Refacciones/5
        [ResponseType(typeof(Refacciones))]
        public IHttpActionResult GetRefacciones(int id)
        {
            Refacciones refacciones = db.Refacciones.Find(id);
            if (refacciones == null)
            {
                return NotFound();
            }

            return Ok(refacciones);
        }

        // PUT: api/Refacciones/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutRefacciones(int id, Refacciones refacciones)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != refacciones.idRefacciones)
            {
                return BadRequest();
            }

            db.Entry(refacciones).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!RefaccionesExists(id))
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

        // POST: api/Refacciones
        [ResponseType(typeof(Refacciones))]
        public IHttpActionResult PostRefacciones(Refacciones refacciones)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Refacciones.Add(refacciones);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (RefaccionesExists(refacciones.idRefacciones))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = refacciones.idRefacciones }, refacciones);
        }

        // DELETE: api/Refacciones/5
        [ResponseType(typeof(Refacciones))]
        public IHttpActionResult DeleteRefacciones(int id)
        {
            Refacciones refacciones = db.Refacciones.Find(id);
            if (refacciones == null)
            {
                return NotFound();
            }

            db.Refacciones.Remove(refacciones);
            db.SaveChanges();

            return Ok(refacciones);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool RefaccionesExists(int id)
        {
            return db.Refacciones.Count(e => e.idRefacciones == id) > 0;
        }
    }
}