import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWypozyczoneMySuffix } from 'app/shared/model/wypozyczone-my-suffix.model';

@Component({
    selector: 'jhi-wypozyczone-my-suffix-detail',
    templateUrl: './wypozyczone-my-suffix-detail.component.html'
})
export class WypozyczoneMySuffixDetailComponent implements OnInit {
    wypozyczone: IWypozyczoneMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ wypozyczone }) => {
            this.wypozyczone = wypozyczone;
        });
    }

    previousState() {
        window.history.back();
    }
}
