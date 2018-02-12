import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CategorieOffreMySuffix } from './categorie-offre-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CategorieOffreMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/categorie-offres';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/categorie-offres';

    constructor(private http: Http) { }

    create(categorieOffre: CategorieOffreMySuffix): Observable<CategorieOffreMySuffix> {
        const copy = this.convert(categorieOffre);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(categorieOffre: CategorieOffreMySuffix): Observable<CategorieOffreMySuffix> {
        const copy = this.convert(categorieOffre);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CategorieOffreMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to CategorieOffreMySuffix.
     */
    private convertItemFromServer(json: any): CategorieOffreMySuffix {
        const entity: CategorieOffreMySuffix = Object.assign(new CategorieOffreMySuffix(), json);
        return entity;
    }

    /**
     * Convert a CategorieOffreMySuffix to a JSON which can be sent to the server.
     */
    private convert(categorieOffre: CategorieOffreMySuffix): CategorieOffreMySuffix {
        const copy: CategorieOffreMySuffix = Object.assign({}, categorieOffre);
        return copy;
    }
}
