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
    public class CategoriasPreventivasController : ApiController
    {
        private AlmacenTAEPIEntities db = new AlmacenTAEPIEntities();

        // GET: api/CategoriasPreventivas
        public IQueryable<CategoriasPreventivas> GetCategoriasPreventivas()
        {
            return db.CategoriasPreventivas;
        }

        // GET: api/CategoriasPreventivas/5
        [ResponseType(typeof(CategoriasPreventivas))]
        public IHttpActionResult GetCategoriasPreventivas(int id)
        {
            CategoriasPreventivas categoriasPreventivas = db.CategoriasPreventivas.Find(id);
            if (categoriasPreventivas == null)
            {
                return NotFound();
            }

            return Ok(categoriasPreventivas);
        }

        // PUT: api/CategoriasPreventivas/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutCategoriasPreventivas(int id, CategoriasPreventivas categoriasPreventivas)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != categoriasPreventivas.idCategoriasPreventivas)
            {
                return BadRequest();
            }

            db.Entry(categoriasPreventivas).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CategoriasPreventivasExists(id))
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

        // POST: api/CategoriasPreventivas
        [ResponseType(typeof(CategoriasPreventivas))]
        public IHttpActionResult PostCategoriasPreventivas(CategoriasPreventivas categoriasPreventivas)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.CategoriasPreventivas.Add(categoriasPreventivas);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = categoriasPreventivas.idCategoriasPreventivas }, categoriasPreventivas);
        }

        // DELETE: api/CategoriasPreventivas/5
        [ResponseType(typeof(CategoriasPreventivas))]
        public IHttpActionResult DeleteCategoriasPreventivas(int id)
        {
            CategoriasPreventivas categoriasPreventivas = db.CategoriasPreventivas.Find(id);
            if (categoriasPreventivas == null)
            {
                return NotFound();
            }

            db.CategoriasPreventivas.Remove(categoriasPreventivas);
            db.SaveChanges();

            return Ok(categoriasPreventivas);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool CategoriasPreventivasExists(int id)
        {
            return db.CategoriasPreventivas.Count(e => e.idCategoriasPreventivas == id) > 0;
        }
    }
}