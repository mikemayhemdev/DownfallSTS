package champ.cards;

import basemod.helpers.CardModifierManager;
import champ.ChampMod;
import champ.util.TechniqueMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Encircle extends AbstractChampCard {

    public final static String ID = makeID("Encircle");

    //stupid intellij stuff attack, all_enemy, uncommon

    private static final int DAMAGE = 4;
    private static final int MAGIC = 2;

    public Encircle() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        myHpLossCost = 2;
      //  tags.add(ChampMod.TECHNIQUE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) techique();

        for (int i = 0; i < magicNumber; i++) {
            atb(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }

        fatigue(2);
        if (bcombo() && !this.purgeOnUse) {
            AbstractCard r = this;
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    GameActionManager.queueExtraCard(r, m);
                }
            });
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor =  bcombo()? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }


    public void upp() {
        tags.add(ChampMod.TECHNIQUE);
        initializeDescription();
    }
}