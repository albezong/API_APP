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
    public class EquiposController : ApiController
    {
        private AlmacenTAEPIEntities db = new AlmacenTAEPIEntities();

        // GET: api/Equipos
        public IQueryable<Equipos> GetEquipos()
        {
            return db.Equipos;
        }

        // GET: api/Equipos/5
        [ResponseType(typeof(Equipos))]
        public IHttpActionResult GetEquipos(int id)
        {
            Equipos equipos = db.Equipos.Find(id);
            if (equipos == null)
            {
                return NotFound();
            }

            return Ok(equipos);
        }

        // PUT: api/Equipos/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutEquipos(int id, Equipos equipos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != equipos.idEquipos)
            {
                return BadRequest();
            }

            db.Entry(equipos).State = EntityState.Modified;

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
        [ResponseType(typeof(Equipos))]
        public IHttpActionResult PostEquipos(Equipos equipos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Equipos.Add(equipos);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = equipos.idEquipos }, equipos);
        }

        // DELETE: api/Equipos/5
        [ResponseType(typeof(Equipos))]
        public IHttpActionResult DeleteEquipos(int id)
        {
            Equipos equipos = db.Equipos.Find(id);
            if (equipos == null)
            {
                return NotFound();
            }

            db.Equipos.Remove(equipos);
            db.SaveChanges();

            return Ok(equipos);
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