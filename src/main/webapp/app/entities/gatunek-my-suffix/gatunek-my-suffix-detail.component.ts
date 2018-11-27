import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGatunekMySuffix } from 'app/shared/model/gatunek-my-suffix.model';

@Component({
    selector: 'jhi-gatunek-my-suffix-detail',
    templateUrl: './gatunek-my-suffix-detail.component.html'
})
export class GatunekMySuffixDetailComponent implements OnInit {
    gatunek: IGatunekMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ gatunek }) => {
            this.gatunek = gatunek;
        });
    }

    previousState() {
        window.history.back();
    }
}
