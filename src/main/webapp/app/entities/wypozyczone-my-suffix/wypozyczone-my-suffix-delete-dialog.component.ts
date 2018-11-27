import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IWypozyczoneMySuffix } from 'app/shared/model/wypozyczone-my-suffix.model';
import { WypozyczoneMySuffixService } from './wypozyczone-my-suffix.service';

@Component({
    selector: 'jhi-wypozyczone-my-suffix-delete-dialog',
    templateUrl: './wypozyczone-my-suffix-delete-dialog.component.html'
})
export class WypozyczoneMySuffixDeleteDialogComponent {
    wypozyczone: IWypozyczoneMySuffix;

    constructor(
        private wypozyczoneService: WypozyczoneMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.wypozyczoneService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'wypozyczoneListModification',
                content: 'Deleted an wypozyczone'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-wypozyczone-my-suffix-delete-popup',
    template: ''
})
export class WypozyczoneMySuffixDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ wypozyczone }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(WypozyczoneMySuffixDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.wypozyczone = wypozyczone;
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
