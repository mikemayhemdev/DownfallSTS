package champ.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BladeFlurry extends AbstractChampCard {

    public final static String ID = makeID("BladeFlurry");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 6;

    public BladeFlurry() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(CardTags.STRIKE);
    }

    //TODO: Timing. This will mess up on itself. Count or don't? Hmmmm

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = 0;
                for (AbstractCard q : p.hand.group) if (q.hasTag(CardTags.STRIKE)) x++;
                for (int i = 0; i < x; i++) att(new DamageAction(m, makeInfo(), AttackEffect.SLASH_DIAGONAL));
            }
        });
    }

    public void applyPowers() {
        super.applyPowers();
        if (AbstractDungeon.player != null) {
            int x = 0;
            if (upgraded){
                this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
                x++;
            } else {
                this.rawDescription = cardStrings.DESCRIPTION;
            }
            for (AbstractCard q : AbstractDungeon.player.hand.group) if (q.hasTag(CardTags.STRIKE)) x++;
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + x;
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}