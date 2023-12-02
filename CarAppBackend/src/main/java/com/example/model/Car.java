package com.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "cars")
@Data
@NoArgsConstructor
@ToString
public class Car {

//	create table cars (id MEDIUMINT NOT NULL AUTO_INCREMENT, make varchar(50) not  null, model varchar(50) not null, description varchar(200) null, is_new boolean default  False, primary key (id));
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "make")
	private String make;

	@Column(name = "model")
	private String model;


	@Column(name = "description")
	private String description;

	@Column(name = "is_new")
	private boolean isNew;

	public Car(String make, String model, String description, boolean isNew) {
		this.make = make;
		this.model = model;
		this.description = description;
		this.isNew = isNew;
	}

	public Car(long id, String make, String model, String description, boolean isNew) {
		super();
		this.id = id;
		this.make = make;
		this.model = model;
		this.description = description;
		this.isNew = isNew;
	}

}
