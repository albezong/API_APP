using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using APIMIRAI_Construcciones.Data;

namespace APIMIRAI_Construcciones.Models
{
    public class RefaccionesDto
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idRefacciones { get; set; }
        public Nullable<int> idfRevisiones { get; set; }
        public Nullable<int> idfUnidades { get; set; }
        public string nombreRefaccion { get; set; }
        public Nullable<int> idfDescripcionPrioridades { get; set; }
        public Nullable<int> cantidad { get; set; }
        public string observaciones { get; set; }
        public Nullable<System.DateTime> fecha { get; set; }
        public int numeroReporte { get; set; }
        public string descripcion { get; set; }

        public RevisionesDto RevisionesDto { get; set; }
        public UnidadesDto UnidadesDto { get; set; }
        public PrioridadesDto PrioridadesDto { get; set; }
    }
}