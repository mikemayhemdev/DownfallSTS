package charbosses.cards.crowbot;

import basemod.abstracts.CustomCard;
import charbosses.bosses.Crowbot.CharBossCrowbot;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.downfallMod;

public class HeavySlug extends CustomCard {
    public static final String ID = "downfall_Charboss:HeavySlug";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }

    public HeavySlug() {
        super(ID, HeavySlug.cardStrings.NAME, "downfallResources/images/cards/crowbot/heavySlug.png", 1, HeavySlug.cardStrings.DESCRIPTION, CardType.ATTACK,CharBossCrowbot.Enums.Crowbot_COLOR, CardRarity.BASIC, CardTarget.ENEMY);
        this.baseDamage = 9;
        this.exhaust = true;
        this.tags.add(CharBossCrowbot.Enums.AMMO);
    }

    @Override
    public void use(final AbstractPlayer p, final AbstractMonster m) {
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(2);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HeavySlug();
    }
}
