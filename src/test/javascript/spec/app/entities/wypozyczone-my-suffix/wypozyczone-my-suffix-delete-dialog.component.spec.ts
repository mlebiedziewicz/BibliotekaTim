/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BibliotekaTimTestModule } from '../../../test.module';
import { WypozyczoneMySuffixDeleteDialogComponent } from 'app/entities/wypozyczone-my-suffix/wypozyczone-my-suffix-delete-dialog.component';
import { WypozyczoneMySuffixService } from 'app/entities/wypozyczone-my-suffix/wypozyczone-my-suffix.service';

describe('Component Tests', () => {
    describe('WypozyczoneMySuffix Management Delete Component', () => {
        let comp: WypozyczoneMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<WypozyczoneMySuffixDeleteDialogComponent>;
        let service: WypozyczoneMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BibliotekaTimTestModule],
                declarations: [WypozyczoneMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(WypozyczoneMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(WypozyczoneMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WypozyczoneMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
