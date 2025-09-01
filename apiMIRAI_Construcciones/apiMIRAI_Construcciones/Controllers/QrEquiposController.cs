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
using APIMIRAI_Construcciones.Helper;
using iTextSharp.text.pdf;
using iTextSharp.text;
using APIMIRAI_Construcciones.Infraestructura;
using APIMIRAI_Construcciones.Services;
using System.IO;
using APIMIRAI_Construcciones.Models;

namespace APIMIRAI_Construcciones.Controllers
{
    [RoutePrefix("api/QrEquipos")]
    public class QrEquiposController : ApiController
    {
        private PruebaAlmacenTAEPIEntities1 db = new PruebaAlmacenTAEPIEntities1();
        private readonly IUnitOfWork _unitOfWork;

        public QrEquiposController()
        {
            _unitOfWork = new UnitOfWork();
        }

        // GET: api/QrEquipos
        [HttpGet]
        [Route("")]
        public IHttpActionResult GetQrEquipos()
        {
            var empresas = db.QrEquipos
                .Select(e => new QrEquiposDto
                {
                    idQrEquipos = e.idQrEquipos,
                    claveQR = e.claveQR,
                    idEquipos = e.idEquipos,
                })
                .ToList();

            return Ok(empresas);
        }

        // GET: api/QrEquipos/5
        //[ResponseType(typeof(QrEquipos))]
        [HttpGet]
        [Route("{id:int}")]
        public IHttpActionResult GetQrEquipos(int id)
        {
            var empresa = db.QrEquipos
        .Where(e => e.idQrEquipos == id)
        .Select(e => new QrEquiposDto
        {
            idQrEquipos = e.idQrEquipos,
            claveQR = e.claveQR,
            idEquipos = e.idEquipos,
        })
        .FirstOrDefault();

            if (empresa == null)
            {
                return NotFound();
            }

            return Ok(empresa);
        }

        // PUT: api/QrEquipos/5
        //[ResponseType(typeof(void))]
        [HttpPut]
        [Route("{id:int}")]
        public IHttpActionResult PutQrEquipos(int id, QrEquiposDto qrEquipos)
        {
            if (!ModelState.IsValid)
                return BadRequest(ModelState);

            var existingQrEquipos = db.QrEquipos.Find(id);
            if (existingQrEquipos == null)
                return NotFound();

            if (id != qrEquipos.idQrEquipos)
                return BadRequest();

            existingQrEquipos.claveQR = qrEquipos.claveQR;
            existingQrEquipos.idEquipos = qrEquipos.idEquipos;

            db.SaveChanges();

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
        //[ResponseType(typeof(QrEquipos))]
        [HttpPost]
        [Route("")]
        public IHttpActionResult PostQrEquipos(QrEquipos qrEquipos)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.QrEquipos.Add(qrEquipos);
            db.SaveChanges();

            return Ok(qrEquipos);
        }

        // DELETE: api/QrEquipos/5
        //[ResponseType(typeof(QrEquipos))]
        [HttpDelete]
        [Route("{id:int}")]
        public IHttpActionResult DeleteQrEquipos(int id)
        {
            var qrEquipos = db.QrEquipos.Find(id);
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

        //-------------------------------------------**********************************

        //Ya esta solo tienes que ejecutar la api y abrir la url https://localhost:44333/api/qr/VerQRenIMG/1
        [HttpGet]
        [Route("api/qr/VerQRenIMG/{id}")]
        //[ResponseType(typeof(void))]
        public HttpResponseMessage VerQRImagen(int id)
        {
            var qrEquipo = db.QrEquipos.FirstOrDefault(e => e.idQrEquipos == id);
            if (qrEquipo == null)
                return new HttpResponseMessage(HttpStatusCode.NotFound);

            if (string.IsNullOrWhiteSpace(qrEquipo.claveQR))
                return new HttpResponseMessage(HttpStatusCode.BadRequest)
                {
                    Content = new StringContent("El QR no existe o no tiene datos.")
                };

            byte[] qrBytes;
            try
            {
                qrBytes = Convert.FromBase64String(qrEquipo.claveQR);
            }
            catch
            {
                return new HttpResponseMessage(HttpStatusCode.BadRequest)
                {
                    Content = new StringContent("El QR almacenado no es válido.")
                };
            }

            var result = new HttpResponseMessage(HttpStatusCode.OK)
            {
                Content = new ByteArrayContent(qrBytes)
            };
            result.Content.Headers.ContentType = new System.Net.Http.Headers.MediaTypeHeaderValue("image/png");
            result.Content.Headers.ContentDisposition = new System.Net.Http.Headers.ContentDispositionHeaderValue("inline")
            {
                FileName = $"QR_{qrEquipo.Equipos?.numIdentificador ?? "Equipo"}.png"
            };

            return result;
        }
    }
}