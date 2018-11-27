import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IWydawnictwoMySuffix } from 'app/shared/model/wydawnictwo-my-suffix.model';

@Component({
    selector: 'jhi-wydawnictwo-my-suffix-detail',
    templateUrl: './wydawnictwo-my-suffix-detail.component.html'
})
export class WydawnictwoMySuffixDetailComponent implements OnInit {
    wydawnictwo: IWydawnictwoMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ wydawnictwo }) => {
            this.wydawnictwo = wydawnictwo;
        });
    }

    previousState() {
        window.history.back();
    }
}
