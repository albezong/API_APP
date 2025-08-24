using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using APIMIRAI_Construcciones.Data;

namespace APIMIRAI_Construcciones.Models
{
    public class DetallesPreventivosDto
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idDetallesPreventivos { get; set; }
        public Nullable<int> idfRevisiones { get; set; }
        public Nullable<int> idfCategoriasPreventivas { get; set; }
        public string nombreParte { get; set; }
        public Nullable<int> idfEstadoPrioridades { get; set; }
        public string comentarios { get; set; }
        public string observaciones { get; set; }
        public Nullable<System.DateTime> fecha { get; set; }
        public Nullable<int> numeroReporte { get; set; }

        public CategoriasPreventivasDto CategoriasPreventivasDto { get; set; }
        public PrioridadesDto PrioridadesDto { get; set; }
        public RevisionesDto RevisionesDto { get; set; }
    }
}