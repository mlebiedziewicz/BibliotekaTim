/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BibliotekaTimTestModule } from '../../../test.module';
import { AutorMySuffixDeleteDialogComponent } from 'app/entities/autor-my-suffix/autor-my-suffix-delete-dialog.component';
import { AutorMySuffixService } from 'app/entities/autor-my-suffix/autor-my-suffix.service';

describe('Component Tests', () => {
    describe('AutorMySuffix Management Delete Component', () => {
        let comp: AutorMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<AutorMySuffixDeleteDialogComponent>;
        let service: AutorMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BibliotekaTimTestModule],
                declarations: [AutorMySuffixDeleteDialogComponent]
            })
                .overrideTemplate(AutorMySuffixDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(AutorMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AutorMySuffixService);
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
