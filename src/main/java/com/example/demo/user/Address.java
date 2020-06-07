package com.example.demo.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Address {

    @Id
    private Long id;

    private String fullAddress;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private User user;

    // Add @Version in order to avoid a select for each insert
    @Version
    private Long version;

}
