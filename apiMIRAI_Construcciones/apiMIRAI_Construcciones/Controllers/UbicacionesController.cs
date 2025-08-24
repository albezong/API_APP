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
    public class UbicacionesController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/Ubicaciones
        public IHttpActionResult GetUbicaciones()
        {
            var empresas = db.Ubicaciones
                .Select(e => new UbicacionesDto
                {
                    idUbicaciones = e.idUbicaciones,
                    nombre = e.nombre,
                })
                .ToList();

            return Ok(empresas);
        }

        // GET: api/Ubicaciones/5
        [ResponseType(typeof(Ubicaciones))]
        public IHttpActionResult GetUbicaciones(int id)
        {
            var empresa = db.Ubicaciones
        .Where(e => e.idUbicaciones == id)
        .Select(e => new UbicacionesDto
        {
            idUbicaciones = e.idUbicaciones,
            nombre = e.nombre,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
        }

        // PUT: api/Ubicaciones/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutUbicaciones(int id, Ubicaciones ubicaciones)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != ubicaciones.idUbicaciones)
            {
                return BadRequest();
            }

            db.Entry(ubicaciones).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!UbicacionesExists(id))
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

        // POST: api/Ubicaciones
        [ResponseType(typeof(Ubicaciones))]
        public IHttpActionResult PostUbicaciones(Ubicaciones ubicaciones)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Ubicaciones.Add(ubicaciones);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = ubicaciones.idUbicaciones }, ubicaciones);
        }

        // DELETE: api/Ubicaciones/5
        [ResponseType(typeof(Ubicaciones))]
        public IHttpActionResult DeleteUbicaciones(int id)
        {
            Ubicaciones ubicaciones = db.Ubicaciones.Find(id);
            if (ubicaciones == null)
            {
                return NotFound();
            }

            db.Ubicaciones.Remove(ubicaciones);
            db.SaveChanges();

            return Ok(ubicaciones);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool UbicacionesExists(int id)
        {
            return db.Ubicaciones.Count(e => e.idUbicaciones == id) > 0;
        }
    }
}