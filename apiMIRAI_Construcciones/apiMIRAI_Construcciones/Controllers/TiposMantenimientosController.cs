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
    [RoutePrefix("api/TiposMantenimientos")]
    public class TiposMantenimientosController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/TiposMantenimientos
        [HttpGet]
        [Route("")]
        public IHttpActionResult GetTiposMantenimientos()
        {
            var empresas = db.TiposMantenimientos
                .Select(e => new TiposMantenimientosDto
                {
                    idTiposMantenimientos = e.idTiposMantenimientos,
                    nombre = e.nombre,
                })
                .ToList();

            return Ok(empresas);
        }

        // GET: api/TiposMantenimientos/5
        //[ResponseType(typeof(TiposMantenimientos))]
        [HttpGet]
        [Route("{id:int}")]
        public IHttpActionResult GetTiposMantenimientos(int id)
        {
            var empresa = db.TiposMantenimientos
        .Where(e => e.idTiposMantenimientos == id)
        .Select(e => new TiposMantenimientosDto
        {
            idTiposMantenimientos = e.idTiposMantenimientos,
            nombre = e.nombre,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
        }

        // PUT: api/TiposMantenimientos/5
        //[ResponseType(typeof(void))]
        [HttpPut]
        [Route("{id:int}")]
        public IHttpActionResult PutTiposMantenimientos(int id, TiposMantenimientosDto tiposMantenimientos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var existingTiposMantenimientos = db.TiposMantenimientos.Find(id);
            if (existingTiposMantenimientos == null)
                return NotFound();

            if (id != tiposMantenimientos.idTiposMantenimientos)
            {
                return BadRequest();
            }

            existingTiposMantenimientos.nombre = tiposMantenimientos.nombre;

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
        //[ResponseType(typeof(TiposMantenimientos))]
        [HttpPost]
        [Route("")]
        public IHttpActionResult PostTiposMantenimientos(TiposMantenimientos tiposMantenimientos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.TiposMantenimientos.Add(tiposMantenimientos);
            db.SaveChanges();

            return Ok(tiposMantenimientos);
        }

        // DELETE: api/TiposMantenimientos/5
        //[ResponseType(typeof(TiposMantenimientos))]
        [HttpDelete]
        [Route("{id:int}")]
        public IHttpActionResult DeleteTiposMantenimientos(int id)
        {
            var tiposMantenimientos = db.TiposMantenimientos.Find(id);
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