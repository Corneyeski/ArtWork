/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArtWorkTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ConnectionDetailComponent } from '../../../../../../main/webapp/app/entities/connection/connection-detail.component';
import { ConnectionService } from '../../../../../../main/webapp/app/entities/connection/connection.service';
import { Connection } from '../../../../../../main/webapp/app/entities/connection/connection.model';

describe('Component Tests', () => {

    describe('Connection Management Detail Component', () => {
        let comp: ConnectionDetailComponent;
        let fixture: ComponentFixture<ConnectionDetailComponent>;
        let service: ConnectionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArtWorkTestModule],
                declarations: [ConnectionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ConnectionService,
                    JhiEventManager
                ]
            }).overrideTemplate(ConnectionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConnectionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConnectionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Connection(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.connection).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
