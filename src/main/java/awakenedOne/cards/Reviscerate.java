package awakenedOne.cards;

import awakenedOne.AwakenedOneMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static awakenedOne.AwakenedOneMod.loadJokeCardImage;
import static awakenedOne.AwakenedOneMod.makeBetaCardPath;

public class Reviscerate extends AbstractAwakenedCard {
    public final static String ID = AwakenedOneMod.makeID(Reviscerate.class.getSimpleName());
    // intellij stuff attack, enemy, basic, 6, 3,  , , ,

    public Reviscerate() {
        super(ID, 3, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 6;
        this.baseMagicNumber = 3;
        this.magicNumber = this.baseMagicNumber;
        tags.add(CardTags.STRIKE);
        loadJokeCardImage(this, makeBetaCardPath(Reviscerate.class.getSimpleName() + ".png"));

        if (CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null) {
            this.configureCostsOnNewCard();
        }
    }

    public void atTurnStart() {
        this.resetAttributes();
        this.applyPowers();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
            dmg(m, AbstractGameAction.AttackEffect.SLASH_HEAVY);
        }
    }

    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        Iterator var1 = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();
        int powers = 0;
        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (c.type == CardType.POWER) {
                powers++;
            }
        }
        this.setCostForTurn(this.cost - powers);
    }


    public void configureCostsOnNewCard() {
        Iterator var1 = AbstractDungeon.actionManager.cardsPlayedThisTurn.iterator();
        int powers = 0;
        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (c.type == CardType.POWER) {
                powers++;
            }
        }
        this.setCostForTurn(this.cost - powers);
    }

    public void triggerOnCardPlayed(AbstractCard c) {
        if (c.type == CardType.POWER) {
            this.setCostForTurn(this.costForTurn - 1);
        }
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }
}