/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ArtWorkTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CommentLikeDetailComponent } from '../../../../../../main/webapp/app/entities/comment-like/comment-like-detail.component';
import { CommentLikeService } from '../../../../../../main/webapp/app/entities/comment-like/comment-like.service';
import { CommentLike } from '../../../../../../main/webapp/app/entities/comment-like/comment-like.model';

describe('Component Tests', () => {

    describe('CommentLike Management Detail Component', () => {
        let comp: CommentLikeDetailComponent;
        let fixture: ComponentFixture<CommentLikeDetailComponent>;
        let service: CommentLikeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ArtWorkTestModule],
                declarations: [CommentLikeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CommentLikeService,
                    JhiEventManager
                ]
            }).overrideTemplate(CommentLikeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CommentLikeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommentLikeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CommentLike(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.commentLike).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
