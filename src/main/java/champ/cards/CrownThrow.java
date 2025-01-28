
package champ.cards;

import basemod.devcommands.draw.Draw;
import basemod.helpers.VfxBuilder;
import champ.ChampMod;
import champ.powers.DoubleStyleThisTurnPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import downfall.util.TextureLoader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static champ.ChampMod.loadJokeCardImage;

public class CrownThrow extends AbstractChampCard {

    public final static String ID = makeID("CrownThrow");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 2;

    private boolean returned;

    public CrownThrow() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = 2;
        tags.add(ChampMod.COMBO);
        tags.add(ChampMod.COMBOBERSERKER);
        loadJokeCardImage(this, "CrownThrow.png");
    }

    public void returned() {

        upgradeBaseCost(0);
        returned = true;
        DESCRIPTION = rawDescription = EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        // First you throw a crown...
        AbstractDungeon.effectList.add(new VfxBuilder(TextureLoader.getTexture("champResources/images/relics/ChampionCrown.png"), p.hb.x + p.hb.width, p.hb.cY, 1.5F)
                .moveX(p.hb.x + p.hb.width, Settings.WIDTH + (128 * Settings.scale))
                .rotate(-300F)
                .build());

        if (bcombo()) atb(new DrawCardAction(magicNumber));
    }

    @Override
    public void triggerOnGlowCheck() {
        glowColor = bcombo() ? GOLD_BORDER_GLOW_COLOR : BLUE_BORDER_GLOW_COLOR;
    }


    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeMagicNumber(1);
    }
}

