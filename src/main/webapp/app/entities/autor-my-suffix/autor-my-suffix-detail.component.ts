import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAutorMySuffix } from 'app/shared/model/autor-my-suffix.model';

@Component({
    selector: 'jhi-autor-my-suffix-detail',
    templateUrl: './autor-my-suffix-detail.component.html'
})
export class AutorMySuffixDetailComponent implements OnInit {
    autor: IAutorMySuffix;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ autor }) => {
            this.autor = autor;
        });
    }

    previousState() {
        window.history.back();
    }
}
