import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAutorMySuffix } from 'app/shared/model/autor-my-suffix.model';
import { AutorMySuffixService } from './autor-my-suffix.service';

@Component({
    selector: 'jhi-autor-my-suffix-delete-dialog',
    templateUrl: './autor-my-suffix-delete-dialog.component.html'
})
export class AutorMySuffixDeleteDialogComponent {
    autor: IAutorMySuffix;

    constructor(private autorService: AutorMySuffixService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.autorService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'autorListModification',
                content: 'Deleted an autor'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-autor-my-suffix-delete-popup',
    template: ''
})
export class AutorMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ autor }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(AutorMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.autor = autor;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
