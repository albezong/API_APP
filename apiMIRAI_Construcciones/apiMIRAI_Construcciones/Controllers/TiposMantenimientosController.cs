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
    public class TiposMantenimientosController : ApiController
    {
        private AlmacenTAEPIEntities db = new AlmacenTAEPIEntities();

        // GET: api/TiposMantenimientos
        public IQueryable<TiposMantenimientos> GetTiposMantenimientos()
        {
            return db.TiposMantenimientos;
        }

        // GET: api/TiposMantenimientos/5
        [ResponseType(typeof(TiposMantenimientos))]
        public IHttpActionResult GetTiposMantenimientos(int id)
        {
            TiposMantenimientos tiposMantenimientos = db.TiposMantenimientos.Find(id);
            if (tiposMantenimientos == null)
            {
                return NotFound();
            }

            return Ok(tiposMantenimientos);
        }

        // PUT: api/TiposMantenimientos/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutTiposMantenimientos(int id, TiposMantenimientos tiposMantenimientos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != tiposMantenimientos.idTiposMantenimientos)
            {
                return BadRequest();
            }

            db.Entry(tiposMantenimientos).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!TiposMantenimientosExists(id))
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

        // POST: api/TiposMantenimientos
        [ResponseType(typeof(TiposMantenimientos))]
        public IHttpActionResult PostTiposMantenimientos(TiposMantenimientos tiposMantenimientos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.TiposMantenimientos.Add(tiposMantenimientos);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = tiposMantenimientos.idTiposMantenimientos }, tiposMantenimientos);
        }

        // DELETE: api/TiposMantenimientos/5
        [ResponseType(typeof(TiposMantenimientos))]
        public IHttpActionResult DeleteTiposMantenimientos(int id)
        {
            TiposMantenimientos tiposMantenimientos = db.TiposMantenimientos.Find(id);
            if (tiposMantenimientos == null)
            {
                return NotFound();
            }

            db.TiposMantenimientos.Remove(tiposMantenimientos);
            db.SaveChanges();

            return Ok(tiposMantenimientos);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool TiposMantenimientosExists(int id)
        {
            return db.TiposMantenimientos.Count(e => e.idTiposMantenimientos == id) > 0;
        }
    }
}