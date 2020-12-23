package champ.cards;

import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BladeFlurry extends AbstractChampCard {

    public final static String ID = makeID("BladeFlurry");

    //stupid intellij stuff attack, enemy, common

    private static final int DAMAGE = 5;

    public BladeFlurry() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        // exhaust = true;
        magicNumber = baseMagicNumber = 2;
        //  tags.add(ChampMod.TECHNIQUE);
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOGLADIATOR);
        tags.add(CardTags.STRIKE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        //finisher();
        // techique();
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                int x = 0;
                for (AbstractCard q : p.hand.group) if (q.hasTag(ChampMod.TECHNIQUE)) x++;
                if (gcombo()) {
                    x = x + magicNumber;
                }
                for (int i = 0; i < x; i++) att(new DamageAction(m, makeInfo(), AttackEffect.SLASH_DIAGONAL));

            }
        });
        //finisher();
    }

    public void applyPowers() {
        super.applyPowers();

        if (AbstractDungeon.player != null) {
            this.rawDescription = upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
            int x = 0;
            for (AbstractCard q : AbstractDungeon.player.hand.group) if (q.hasTag(ChampMod.TECHNIQUE)) x++;
            if (gcombo()) {
                x += magicNumber;
            }
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + x;
            this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[1];

            this.initializeDescription();
        }
    }

    public void onMoveToDiscard() {
        this.rawDescription = upgraded ? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        this.initializeDescription();
    }


    @Override
    public void triggerOnGlowCheck() {
        glowColor = gcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}