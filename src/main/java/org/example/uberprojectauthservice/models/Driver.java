
package org.example.uberprojectauthservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver extends BaseModal {
    private String name;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    private String phoneNumber;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY) // by making fetch eager now we also fetch all the details that are related to that table like in twitter if u fetch user detail then it will also fetch comments detail, post details, etc.
    // It uses join for merging details from other tables.
    @Fetch(FetchMode.SUBSELECT)
    private List<Booking> bookings;
}
