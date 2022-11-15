package champ.cards;

import basemod.helpers.VfxBuilder;
import champ.ChampMod;
import champ.powers.BoomerangPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.DrawPileToHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.TextureLoader;

public class CrownThrow extends AbstractChampCard {

    public final static String ID = makeID("CrownThrow");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;

    private boolean returned;

    public CrownThrow() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        tags.add(ChampMod.COMBO);
        cardsToPreview = new CrownThrowReturn();
    }

    public void returned() {

        upgradeBaseCost(0);
        returned = true;
        DESCRIPTION = rawDescription = EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL);
        combo();
        // First you throw a crown...
        AbstractDungeon.effectList.add(new VfxBuilder(TextureLoader.getTexture("champResources/images/relics/ChampionCrown.png"), p.hb.x + p.hb.width, p.hb.cY, 1.5F)
                .moveX(p.hb.x + p.hb.width, Settings.WIDTH + (128 * Settings.scale))
                .rotate(-300F)
                .build());

        applyToSelf(new BoomerangPower());
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        cardsToPreview.upgrade();
    }
}

