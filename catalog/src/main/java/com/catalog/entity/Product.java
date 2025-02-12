package com.catalog.entity;


import com.sharedLibrary.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product")
@Getter@Setter@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "category_id")
	private Integer categoryId;
	
	@Column(name = "subcategory_id")
	private Integer subcategoryId;
	
	@Column(name = "brand_id")
	private int brandId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "image_path")
	private String imagePath;
	
	@Column(name = "cal_grm")
	private String calGrm;
	
	@Column(name = "cal_percent")
	private String calPercent;
	
	@Column(name = "fat_grm")
	private String fatGrm;
	
	@Column(name = "fat_percent")
	private String fatPercent;
	
	@Column(name = "cards_grm")
	private String carbsGrm;
	
	@Column(name = "cards_percent")
	private String carbsPercent;
	
	@Column(name = "protein_grm")
	private String proteinGrm;
	
	@Column(name = "protein_percent")
	private String proteinPercent;
	
	@Column(name = "summary")
	private String summary;

	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "status")
	private String status;
	
//	@OneToMany(mappedBy = "productId",cascade = CascadeType.ALL,orphanRemoval = true)
//	@JsonIgnoreProperties("productId,")
//    @JsonIgnore()
//	private List<ProductImage> productImages;

}
