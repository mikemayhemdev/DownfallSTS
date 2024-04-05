package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theHexaghost.HexaMod;
import theHexaghost.actions.DrawEtherealAction;

import java.util.Iterator;

public class StrikeFromBeyond extends AbstractHexaCard {

    public final static String ID = makeID("StrikeFromBeyond");

    private static final int DAMAGE = 4;
    private static final int MAGIC = 2;
    private static final int UPG_DAMAGE = 3;

    public StrikeFromBeyond() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.SELF_AND_ENEMY);
        baseDamage = DAMAGE;
//        baseMagicNumber = magicNumber = MAGIC;
        this.tags.add(CardTags.STRIKE);
        HexaMod.loadJokeCardImage(this, "StrikeFromBeyond.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        atb(new DrawEtherealAction(1));
//        addToBot(new AbstractGameAction() {
//            @Override
//            public void update() {
//                isDone = true;
//                if(!AbstractDungeon.player.drawPile.isEmpty()){
//
//                }
//                if (!AbstractDungeon.player.exhaustPile.isEmpty()) {
//                    ArrayList<AbstractCard> eligible = AbstractDungeon.player.exhaustPile.group.stream().filter(c -> c.hasTag(HexaMod.AFTERLIFE)).collect(Collectors.toCollection(ArrayList::new));  // Very proud of this line
//                    if (!eligible.isEmpty()) {
//                        AbstractCard q = eligible.get(AbstractDungeon.cardRandomRng.random(eligible.size()-1));
//                        att(new AbstractGameAction() {
//                            @Override
//                            public void update() {
//                                isDone = true;
//                                AbstractDungeon.player.exhaustPile.removeCard(q);
//                                AbstractDungeon.effectsQueue.add( new ShowCardAndAddToDrawPileEffect( q.makeSameInstanceOf(), true, false ) );
////                                AbstractDungeon.effectsQueue.add(new ShowCardAndAddToDiscardEffect(q.makeSameInstanceOf()));
//                            }
//                        });
//                    }
//                }
//            }
//        });
//        addToBot(new AbstractGameAction() {
//
//            public void update() {
//                if (AbstractDungeon.player.drawPile.isEmpty()) {
//                    this.isDone = true;
//                    return;
//                }
//                int counter = 0;
//                CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
//                Iterator var2 = AbstractDungeon.player.drawPile.group.iterator();
//
//                AbstractCard card;
//                while(var2.hasNext() && counter < amount) {
//                    card = (AbstractCard)var2.next();
//                    if (card.isEthereal) {
//                        tmp.addToRandomSpot(card);
//                        counter++;
//                    }
//                }
//
//                if (tmp.size() == 0) {
//                    this.isDone = true;
//                    return;
//                }
//
//                for(int i = 0; i < counter; ++i) {
//                    if (!tmp.isEmpty()) {
//                        tmp.shuffle();
//                        card = tmp.getBottomCard();
//                        tmp.removeCard(card);
//                        if (AbstractDungeon.player.hand.size() == 10) {
//                            AbstractDungeon.player.createHandIsFullDialog();
//                        } else {
//                            p.drawPile.group.remove(card);
//                            p.drawPile.addToTop(card);
//                            this.addToBot(new DrawCardAction(1));
//                        }
//                    }
//                }
//                this.isDone = true;
//            }
//        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
        }
    }
}