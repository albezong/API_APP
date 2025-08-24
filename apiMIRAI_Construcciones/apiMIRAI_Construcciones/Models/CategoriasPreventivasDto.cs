using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using Newtonsoft.Json;

namespace APIMIRAI_Construcciones.Models
{
    public class CategoriasPreventivasDto
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idCategoriasPreventivas { get; set; }
        public string nombreCategorias { get; set; }

        [JsonIgnore]
        public ICollection<DetallesPreventivosDto> DetallesPreventivosDto { get; set; }
    }
}