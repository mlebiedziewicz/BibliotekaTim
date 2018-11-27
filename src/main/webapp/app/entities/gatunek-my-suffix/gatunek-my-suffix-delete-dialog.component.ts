import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGatunekMySuffix } from 'app/shared/model/gatunek-my-suffix.model';
import { GatunekMySuffixService } from './gatunek-my-suffix.service';

@Component({
    selector: 'jhi-gatunek-my-suffix-delete-dialog',
    templateUrl: './gatunek-my-suffix-delete-dialog.component.html'
})
export class GatunekMySuffixDeleteDialogComponent {
    gatunek: IGatunekMySuffix;

    constructor(
        private gatunekService: GatunekMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.gatunekService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'gatunekListModification',
                content: 'Deleted an gatunek'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-gatunek-my-suffix-delete-popup',
    template: ''
})
export class GatunekMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ gatunek }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GatunekMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.gatunek = gatunek;
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
