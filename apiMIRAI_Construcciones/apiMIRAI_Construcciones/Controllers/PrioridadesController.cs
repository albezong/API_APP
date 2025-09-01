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
    [RoutePrefix("api/Prioridades")]
    public class PrioridadesController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/Prioridades
        [HttpGet]
        [Route("")]
        public IHttpActionResult GetPrioridades()
        {
            var empresas = db.Prioridades
                .Select(e => new PrioridadesDto
                {
                    idPrioridades = e.idPrioridades,
                    nombre = e.nombre,
                })
                .ToList();

            return Ok(empresas);
        }

        // GET: api/Prioridades/5
        [HttpGet]
        [Route("{id:int}")]
        public IHttpActionResult GetPrioridades(int id)
        {
            var empresa = db.Prioridades
        .Where(e => e.idPrioridades == id)
        .Select(e => new PrioridadesDto
        {
            idPrioridades = e.idPrioridades,
            nombre = e.nombre,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
        }

        // PUT: api/Prioridades/5
        //[ResponseType(typeof(void))]
        [HttpPut]
        [Route("{id:int}")]
        public IHttpActionResult PutPrioridades(int id, PrioridadesDto prioridades)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var existingPrioridades = db.Prioridades.Find(id);
            if (existingPrioridades == null)
                return NotFound();

            if (id != prioridades.idPrioridades)
                return BadRequest();

            existingPrioridades.nombre = prioridades.nombre;

            db.SaveChanges();

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PrioridadesExists(id))
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

        // POST: api/Prioridades
        //[ResponseType(typeof(Prioridades))]
        [HttpPost]
        [Route("")]
        public IHttpActionResult PostPrioridades(Prioridades prioridades)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            db.Prioridades.Add(prioridades);
            db.SaveChanges();

            return Ok(prioridades);
        }

        // DELETE: api/Prioridades/5
        //[ResponseType(typeof(Prioridades))]
        [HttpDelete]
        [Route("{id:int}")]
        public IHttpActionResult DeletePrioridades(int id)
        {
            var prioridades = db.Prioridades.Find(id);
            if (prioridades == null)
                return NotFound();

            db.Prioridades.Remove(prioridades);
            db.SaveChanges();

            return Ok(prioridades);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool PrioridadesExists(int id)
        {
            return db.Prioridades.Count(e => e.idPrioridades == id) > 0;
        }
    }
}