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
    public class UnidadesController : ApiController
    {
        private AlmacenTAEPIEntities db = new AlmacenTAEPIEntities();

        // GET: api/Unidades
        public IQueryable<Unidades> GetUnidades()
        {
            return db.Unidades;
        }

        // GET: api/Unidades/5
        [ResponseType(typeof(Unidades))]
        public IHttpActionResult GetUnidades(int id)
        {
            Unidades unidades = db.Unidades.Find(id);
            if (unidades == null)
            {
                return NotFound();
            }

            return Ok(unidades);
        }

        // PUT: api/Unidades/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutUnidades(int id, Unidades unidades)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != unidades.idUnidades)
            {
                return BadRequest();
            }

            db.Entry(unidades).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UnidadesExists(id))
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

        // POST: api/Unidades
        [ResponseType(typeof(Unidades))]
        public IHttpActionResult PostUnidades(Unidades unidades)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Unidades.Add(unidades);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = unidades.idUnidades }, unidades);
        }

        // DELETE: api/Unidades/5
        [ResponseType(typeof(Unidades))]
        public IHttpActionResult DeleteUnidades(int id)
        {
            Unidades unidades = db.Unidades.Find(id);
            if (unidades == null)
            {
                return NotFound();
            }

            db.Unidades.Remove(unidades);
            db.SaveChanges();

            return Ok(unidades);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool UnidadesExists(int id)
        {
            return db.Unidades.Count(e => e.idUnidades == id) > 0;
        }
    }
}