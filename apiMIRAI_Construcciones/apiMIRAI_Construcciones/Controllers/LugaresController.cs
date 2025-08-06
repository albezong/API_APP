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
    public class LugaresController : ApiController
    {
        private AlmacenTAEPIEntities db = new AlmacenTAEPIEntities();

        // GET: api/Lugares
        public IQueryable<Lugares> GetLugares()
        {
            return db.Lugares;
        }

        // GET: api/Lugares/5
        [ResponseType(typeof(Lugares))]
        public IHttpActionResult GetLugares(int id)
        {
            Lugares lugares = db.Lugares.Find(id);
            if (lugares == null)
            {
                return NotFound();
            }

            return Ok(lugares);
        }

        // PUT: api/Lugares/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutLugares(int id, Lugares lugares)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != lugares.idLugares)
            {
                return BadRequest();
            }

            db.Entry(lugares).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!LugaresExists(id))
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

        // POST: api/Lugares
        [ResponseType(typeof(Lugares))]
        public IHttpActionResult PostLugares(Lugares lugares)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Lugares.Add(lugares);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = lugares.idLugares }, lugares);
        }

        // DELETE: api/Lugares/5
        [ResponseType(typeof(Lugares))]
        public IHttpActionResult DeleteLugares(int id)
        {
            Lugares lugares = db.Lugares.Find(id);
            if (lugares == null)
            {
                return NotFound();
            }

            db.Lugares.Remove(lugares);
            db.SaveChanges();

            return Ok(lugares);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool LugaresExists(int id)
        {
            return db.Lugares.Count(e => e.idLugares == id) > 0;
        }
    }
}