import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IKsiazkaMySuffix } from 'app/shared/model/ksiazka-my-suffix.model';

@Component({
    selector: 'jhi-ksiazka-my-suffix-detail',
    templateUrl: './ksiazka-my-suffix-detail.component.html'
})
export class KsiazkaMySuffixDetailComponent implements OnInit {
    ksiazka: IKsiazkaMySuffix;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ ksiazka }) => {
            this.ksiazka = ksiazka;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
