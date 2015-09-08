# NLP query parser 

This is a query parser aimed at NLP applications.  

The usage is very easy: 

```
Parser.parse("NN NP JJ")
```
will produce 
```
Concatenate(List(Pos(NN), Chunk(NP), Pos(JJ)))
```

## Regular expression commands 

## Supported annotations: 
- Raw text 
- Part of Speech tags 
- Chunks  

## TODO annotations 
- NER 
- Coreference 
- SRL 
- Subj-Obj 
- Wikifier
