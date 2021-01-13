package champ.cards;

import champ.ChampMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MurderStrike extends AbstractChampCard {

    public final static String ID = makeID("MurderStrike");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 15;

    public MurderStrike() {
        super(ID, 8, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        selfRetain = true;
        exhaust = true;
        tags.add(CardTags.STRIKE);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);
        if (c.hasTag(ChampMod.TECHNIQUE) && AbstractDungeon.player.hand.group.contains(this)) {
            {
                updateCost(-1);
                baseDamage += magicNumber;
                applyPowers();
                superFlash(Color.RED.cpy());

            }
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_VERTICAL);
    }

    public void upp() {
        if (this.cost < 8) {
            this.upgradeBaseCost(this.cost - 2);
            if (this.cost < 0) {
                this.cost = 0;
            }
        } else {
            this.upgradeBaseCost(6);
            this.upgradeDamage(6);
        }
    }
}