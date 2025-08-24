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
using APIMIRAI_Construcciones.Data;
using APIMIRAI_Construcciones.Models;

namespace APIMIRAI_Construcciones.Controllers
{
    public class TareasController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/Tareas
        public IHttpActionResult GetTareas()
        {
            var empresas = db.Tareas
                .Select(e => new TareasDto
                {
                    idTareas = e.idTareas,
                    idTiposMantenimientos = e.idTiposMantenimientos,
                    descripcion = e.descripcion,
                })
                .ToList();

            return Ok(empresas);
        }

        // GET: api/Tareas/5
        [ResponseType(typeof(Tareas))]
        public IHttpActionResult GetTareas(int id)
        {
            var empresa = db.Tareas
        .Where(e => e.idTareas == id)
        .Select(e => new TareasDto
        {
            idTareas = e.idTareas,
            idTiposMantenimientos = e.idTiposMantenimientos,
            descripcion = e.descripcion,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
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
            db.SaveChanges();

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