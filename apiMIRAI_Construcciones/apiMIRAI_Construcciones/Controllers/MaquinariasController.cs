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
using apiMIRAI_Construcciones.Data.QRDB;

using apiMIRAI_Construcciones.Models.QRDB;
//using apiMIRAI_Construcciones.Data.QRDB;
using Maquinarias = apiMIRAI_Construcciones.Data.QRDB.Maquinarias;

namespace apiMIRAI_Construcciones.Controllers
{
    public class MaquinariasController : ApiController
    {
        #region DbContext
        private QrDBEntities db = new QrDBEntities();
        #endregion


        // GET: api/Maquinarias
        public IQueryable<Maquinarias> GetMaquinarias()
        {
            return db.Maquinarias;
        }

        // GET: api/Maquinarias/5
        [ResponseType(typeof(Maquinarias))]
        public IHttpActionResult GetMaquinarias(int id)
        {
            Maquinarias maquinarias = db.Maquinarias.Find(id);
            if (maquinarias == null)
            {
                return NotFound();
            }

            return Ok(maquinarias);
        }

        // PUT: api/Maquinarias/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutMaquinarias(int id, Maquinarias maquinarias)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != maquinarias.id)
            {
                return BadRequest();
            }

            db.Entry(maquinarias).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!MaquinariasExists(id))
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

        // POST: api/Maquinarias
        [ResponseType(typeof(Maquinarias))]
        public IHttpActionResult PostMaquinarias(Maquinarias maquinarias)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Maquinarias.Add(maquinarias);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = maquinarias.id }, maquinarias);
        }

        // DELETE: api/Maquinarias/5
        [ResponseType(typeof(Maquinarias))]
        public IHttpActionResult DeleteMaquinarias(int id)
        {
            Maquinarias maquinarias = db.Maquinarias.Find(id);
            if (maquinarias == null)
            {
                return NotFound();
            }

            db.Maquinarias.Remove(maquinarias);
            db.SaveChanges();

            return Ok(maquinarias);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool MaquinariasExists(int id)
        {
            return db.Maquinarias.Count(e => e.id == id) > 0;
        }
    }
}