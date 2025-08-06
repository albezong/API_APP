using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class CategoriasPreventivasDto
	{
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idCategoriasPreventivas { get; set; }
        public string nombreCategorias { get; set; }

        public ICollection<DetallesPreventivosDto> DetallesPreventivosDto { get; set; }
    }
}