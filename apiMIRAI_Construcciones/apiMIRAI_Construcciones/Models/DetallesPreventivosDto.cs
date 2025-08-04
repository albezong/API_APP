using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class DetallesPreventivosDto
	{
        public int idDetallesPreventivos { get; set; }
        public Nullable<int> idfRevisiones { get; set; }
        public Nullable<int> idfCategoriasPreventivas { get; set; }
        public string nombreParte { get; set; }
        public string estado { get; set; }
        public string comentarios { get; set; }
        public string observaciones { get; set; }
        public Nullable<System.DateTime> fecha { get; set; }
        public Nullable<int> numeroReporte { get; set; }

        public CategoriasPreventivasDto CategoriasPreventivasDto { get; set; }
        public RevisionesDto RevisionesDto { get; set; }
    }
}