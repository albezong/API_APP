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
    public class QrEquiposController : ApiController
    {
        private AlmacenTAEPIEntities db = new AlmacenTAEPIEntities();

        // GET: api/QrEquipos
        public IQueryable<QrEquipos> GetQrEquipos()
        {
            return db.QrEquipos;
        }

        // GET: api/QrEquipos/5
        [ResponseType(typeof(QrEquipos))]
        public IHttpActionResult GetQrEquipos(int id)
        {
            QrEquipos qrEquipos = db.QrEquipos.Find(id);
            if (qrEquipos == null)
            {
                return NotFound();
            }

            return Ok(qrEquipos);
        }

        // PUT: api/QrEquipos/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutQrEquipos(int id, QrEquipos qrEquipos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != qrEquipos.idQrEquipos)
            {
                return BadRequest();
            }

            db.Entry(qrEquipos).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!QrEquiposExists(id))
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

        // POST: api/QrEquipos
        [ResponseType(typeof(QrEquipos))]
        public IHttpActionResult PostQrEquipos(QrEquipos qrEquipos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.QrEquipos.Add(qrEquipos);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = qrEquipos.idQrEquipos }, qrEquipos);
        }

        // DELETE: api/QrEquipos/5
        [ResponseType(typeof(QrEquipos))]
        public IHttpActionResult DeleteQrEquipos(int id)
        {
            QrEquipos qrEquipos = db.QrEquipos.Find(id);
            if (qrEquipos == null)
            {
                return NotFound();
            }

            db.QrEquipos.Remove(qrEquipos);
            db.SaveChanges();

            return Ok(qrEquipos);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool QrEquiposExists(int id)
        {
            return db.QrEquipos.Count(e => e.idQrEquipos == id) > 0;
        }
    }
}