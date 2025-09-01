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
    [RoutePrefix("api/Lugares")]
    public class LugaresController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/Lugares
        [HttpGet]
        [Route("")]
        public IHttpActionResult GetLugares()
        {
            var empresas = db.Lugares
                .Select(e => new LugaresDto
                {
                    idLugares = e.idLugares,
                    nombreLugar = e.nombreLugar,
                    idfEquipos = e.idfEquipos,
                })
                .ToList();

            return Ok(empresas);
        }

        // GET: api/Lugares/5
        //[ResponseType(typeof(Lugares))]
        [HttpGet]
        [Route("{id:int}")]
        public IHttpActionResult GetLugares(int id)
        {
            var empresa = db.Lugares
        .Where(e => e.idLugares == id)
        .Select(e => new LugaresDto
        {
            idLugares = e.idLugares,
            nombreLugar = e.nombreLugar,
            idfEquipos = e.idfEquipos,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
        }

        // PUT: api/Lugares/5
        //[ResponseType(typeof(void))]
        [HttpPut]
        [Route("{id:int}")]
        public IHttpActionResult PutLugares(int id, LugaresDto lugares)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var existingLugares = db.Lugares.Find(id);
            if (existingLugares == null)
                return NotFound();

            if (id != lugares.idLugares)
                return BadRequest();

            existingLugares.idLugares = lugares.idLugares;
            existingLugares.nombreLugar = lugares.nombreLugar;

            db.SaveChanges();

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
        //[ResponseType(typeof(Lugares))]
        [HttpPost]
        [Route("")]
        public IHttpActionResult PostLugares(Lugares lugares)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            db.Lugares.Add(lugares);
            db.SaveChanges();

            return Ok(lugares);
        }

        // DELETE: api/Lugares/5
        //[ResponseType(typeof(Lugares))]
        [HttpDelete]
        [Route("{id:int}")]
        public IHttpActionResult DeleteLugares(int id)
        {
            var lugares = db.Lugares.Find(id);
            if (lugares == null)
                return NotFound();

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