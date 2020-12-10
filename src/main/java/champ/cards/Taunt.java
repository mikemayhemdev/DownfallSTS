package champ.cards;

import basemod.helpers.CardModifierManager;
import champ.ChampMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.stances.NeutralStance;
import downfall.util.EtherealMod;
import sneckomod.util.ExhaustMod;

public class Taunt extends AbstractChampCard {

    public final static String ID = makeID("Taunt");

    //stupid intellij stuff ATTACK, ENEMY, STARTER

    public Taunt() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        tags.add(ChampMod.TECHNIQUE);
        AbstractCard c = new StanceDance();
        CardModifierManager.addModifier(c, new EtherealMod());
        CardModifierManager.addModifier(c, new ExhaustMod());
        cardsToPreview = c;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        techique();
        if (upgraded) {
            for (AbstractMonster q : monsterList()) {
                applyToEnemy(q, autoWeak(q, 1));
            }
        } else {
            applyToEnemy(m, autoWeak(m, 1));
        }
        AbstractCard c = new StanceDance();
        CardModifierManager.addModifier(c, new EtherealMod());
        CardModifierManager.addModifier(c, new ExhaustMod());
        atb(new MakeTempCardInHandAction(c));
    }

    public void upp() {
        target = CardTarget.ALL_ENEMY;
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}