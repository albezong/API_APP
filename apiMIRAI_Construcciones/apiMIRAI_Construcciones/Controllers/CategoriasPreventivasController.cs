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
    public class CategoriasPreventivasController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/CategoriasPreventivas
        public IHttpActionResult GetCategoriasPreventivas()
        {
            var catPreventivas = db.CategoriasPreventivas
                .Select(e => new CategoriasPreventivasDto
                {
                    idCategoriasPreventivas = e.idCategoriasPreventivas,
                    nombreCategorias = e.nombreCategorias,
                })
                .ToList();

            return Ok(catPreventivas);
        }

        // GET: api/CategoriasPreventivas/5
        [ResponseType(typeof(CategoriasPreventivas))]
        public IHttpActionResult GetCategoriasPreventivas(int id)
        {
            var empresa = db.CategoriasPreventivas
        .Where(e => e.idCategoriasPreventivas== id)
        .Select(e => new CategoriasPreventivasDto
        {
            idCategoriasPreventivas = e.idCategoriasPreventivas,
            nombreCategorias = e.nombreCategorias,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
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