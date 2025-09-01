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
    [RoutePrefix("api/Refacciones")]
    public class RefaccionesController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();

        // GET: api/Refacciones
        [HttpGet]
        [Route("")]
        public IHttpActionResult GetRefacciones()
        {
            var empresas = db.Refacciones
                .Select(e => new RefaccionesDto
                {
                    idRefacciones = e.idRefacciones,
                    idfRevisiones = e.idfRevisiones,
                    idfUnidades = e.idfUnidades,
                    nombreRefaccion = e.nombreRefaccion,
                    idfDescripcionPrioridades = e.idfDescripcionPrioridades,
                    cantidad = e.cantidad,
                    observaciones = e.observaciones,
                    fecha = e.fecha,
                    numeroReporte = e.numeroReporte,
                    descripcion = e.descripcion,
                })
                .ToList();

            return Ok(empresas);
        }

        // GET: api/Refacciones/5
        //[ResponseType(typeof(Refacciones))]
        [HttpGet]
        [Route("{id:int}")]
        public IHttpActionResult GetRefacciones(int id)
        {
            var empresa = db.Refacciones
        .Where(e => e.idRefacciones == id)
        .Select(e => new RefaccionesDto
        {
            idRefacciones = e.idRefacciones,
            idfRevisiones = e.idfRevisiones,
            idfUnidades = e.idfUnidades,
            nombreRefaccion = e.nombreRefaccion,
            idfDescripcionPrioridades = e.idfDescripcionPrioridades,
            cantidad = e.cantidad,
            observaciones = e.observaciones,
            fecha = e.fecha,
            numeroReporte  = e.numeroReporte,
            descripcion = e.descripcion,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
        }

        // PUT: api/Refacciones/5
        //[ResponseType(typeof(void))]
        [HttpPut]
        [Route("{id:int}")]
        public IHttpActionResult PutRefacciones(int id, RefaccionesDto refacciones)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var existingRefacciones = db.Refacciones.Find(id);
            if (existingRefacciones == null)
                return NotFound();

            if (id != refacciones.idRefacciones)
            {
                return BadRequest();
            }

            existingRefacciones.idfRevisiones = refacciones.idfRevisiones;
            existingRefacciones.idfUnidades = refacciones.idfUnidades;
            existingRefacciones.nombreRefaccion = refacciones.nombreRefaccion;
            existingRefacciones.idfDescripcionPrioridades = refacciones.idfDescripcionPrioridades;
            existingRefacciones.cantidad = refacciones.cantidad;
            existingRefacciones.observaciones = refacciones.observaciones;
            existingRefacciones.fecha = refacciones.fecha;
            existingRefacciones.numeroReporte = refacciones.numeroReporte;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!RefaccionesExists(id))
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

        // POST: api/Refacciones
        //[ResponseType(typeof(Refacciones))]
        [HttpPost]
        [Route("")]
        public IHttpActionResult PostRefacciones(Refacciones refacciones)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            /*var numeroReporteService = new SumarNoReporte();
            refacciones.numeroReporte = numeroReporteService.GenerarNuevoNumeroReporte();*/

            db.Refacciones.Add(refacciones);
            db.SaveChanges();

            return Ok(refacciones);
        }

        // DELETE: api/Refacciones/5
        //[ResponseType(typeof(Refacciones))]
        [HttpDelete]
        [Route("{id:int}")]
        public IHttpActionResult DeleteRefacciones(int id)
        {
            var refacciones = db.Refacciones.Find(id);
            if (refacciones == null)
            {
                return NotFound();
            }

            db.Refacciones.Remove(refacciones);
            db.SaveChanges();

            return Ok(refacciones);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool RefaccionesExists(int id)
        {
            return db.Refacciones.Count(e => e.idRefacciones == id) > 0;
        }

        [HttpGet]
        [Route("api/UltimoReporte")]
        public IHttpActionResult UltimoReporte()
        {
            int ultimoRefacciones = db.Refacciones.Any()
                ? db.Refacciones.Max(r => r.numeroReporte): 0;

            //return ultimoRefacciones;
            return Ok(ultimoRefacciones);
        }
    }
}