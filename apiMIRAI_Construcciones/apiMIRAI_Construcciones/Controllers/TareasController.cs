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
    public class TareasController : ApiController
    {
        private AlmacenTAEPIEntities db = new AlmacenTAEPIEntities();

        // GET: api/Tareas
        public IQueryable<Tareas> GetTareas()
        {
            return db.Tareas;
        }

        // GET: api/Tareas/5
        [ResponseType(typeof(Tareas))]
        public IHttpActionResult GetTareas(int id)
        {
            Tareas tareas = db.Tareas.Find(id);
            if (tareas == null)
            {
                return NotFound();
            }

            return Ok(tareas);
        }

        // PUT: api/Tareas/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutTareas(int id, Tareas tareas)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != tareas.idTareas)
            {
                return BadRequest();
            }

            db.Entry(tareas).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!TareasExists(id))
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

        // POST: api/Tareas
        [ResponseType(typeof(Tareas))]
        public IHttpActionResult PostTareas(Tareas tareas)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Tareas.Add(tareas);

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateException)
            {
                if (TareasExists(tareas.idTareas))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return CreatedAtRoute("DefaultApi", new { id = tareas.idTareas }, tareas);
        }

        // DELETE: api/Tareas/5
        [ResponseType(typeof(Tareas))]
        public IHttpActionResult DeleteTareas(int id)
        {
            Tareas tareas = db.Tareas.Find(id);
            if (tareas == null)
            {
                return NotFound();
            }

            db.Tareas.Remove(tareas);
            db.SaveChanges();

            return Ok(tareas);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool TareasExists(int id)
        {
            return db.Tareas.Count(e => e.idTareas == id) > 0;
        }
    }
}